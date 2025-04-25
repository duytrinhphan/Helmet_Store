<script setup>
import {useBanHangTaiQuayStore} from "@/service/Admin/BanHangTaiQuay/BanHangTaiQuayService";
import {useToast} from "primevue/usetoast";
import {computed, inject, onBeforeMount, ref} from "vue";
import {FilterMatchMode} from "primevue/api";

const store = useBanHangTaiQuayStore();
const toast = useToast();
const dsKH = computed(() => store.dsKH);
const selectedKhachHang = ref();
const tableKHEditingRows = ref([]);
const dialogRef = inject("dialogRef");
const khachHangFilter = ref();

onBeforeMount(() => {
  store.loadKH();
});

const initKhachHangFilter = () => {
  khachHangFilter.value = {
    global: {value: null, matchMode: FilterMatchMode.CONTAINS},
  }
};
initKhachHangFilter();

const onRowEditInit = (event) => {
  dialogRef.value.options.props.closable = false;
  if (tableKHEditingRows.value.length > 1) {
    tableKHEditingRows.value.pop();
    toast.add({severity: 'error', summary: 'Không thể thực hiện', detail: 'Vui lòng hoàn tất chỉnh sửa', life: 3000});
  }
}

const tenError = ref('');
const sdtError = ref('');
const onRowEditSave = async (event) => {
  
  if (event.data.ten == null || event.data.ten == '') {
    tenError.value = 'tên không được để trống';
    tableKHEditingRows.value = [store.dsKH[event.index]];
    return;
  } else {
    tenError.value = null;
  }
  if (event.data.sdt == null || event.data.sdt == '') {
    sdtError.value = 'số điện thoại không được để trống';
    tableKHEditingRows.value = [store.dsKH[event.index]];
    return;
  } else {
    sdtError.value = null;
  }

  const res = dsKH.value.find(x=> x.sdt === event.data.sdt && x.id != null);
   if(res != null) {
    sdtError.value = 'số điện thoại đã tồn tại';
    tableKHEditingRows.value = [store.dsKH[event.index]];
    return;
   }else{
    sdtError.value = null;
   }
  let regex = /^(0|\+84)[-.\d]{9,10}$/;
  if (regex.test(event.data.sdt)) {
    sdtError.value = null;
  } else {

    sdtError.value = 'số điện thoại không đúng định dạng';
    tableKHEditingRows.value = [store.dsKH[event.index]];
    return;
  }


  dialogRef.value.options.props.closable = true;
  if (event.data.id === null) {
    await store.addOrUpdateKH(event.data);
    if (event.data.id === null) toast.add({
      severity: 'success',
      summary: 'Thành công',
      detail: 'Thông tin KH đã được lưu vào hệ thống',
      life: 3000
    });
  } else if (JSON.stringify(event.data) === JSON.stringify(event.newData)) {
    if (event.data.id !== null) {
      toast.add({severity: 'info', summary: 'Thông báo', detail: 'Không có thay đổi', life: 3000});
      return;
    } else {
      store.dsKH.shift();
      return;
    }
  } else {
    try {
      await store.addOrUpdateKH(event.data);
      if (event.data.id === null) toast.add({
        severity: 'success',
        summary: 'Thành công',
        detail: 'Thông tin KH đã được lưu vào hệ thống',
        life: 3000
      });
      else toast.add({
        severity: 'success',
        summary: 'Thành công',
        detail: 'Hoàn tất cập nhật thông tin KH',
        life: 3000
      });
    } catch (err) {
      if (event.data.id === null) tableKHEditingRows.value = [event.data];
      else {
        store.dsKH[event.index] = event.data;
        tableKHEditingRows.value = [event.data];
      }
      toast.add({severity: 'error', summary: 'Đã xảy ra lỗi', detail: err.response.data, life: 3000});
    }
  }
}

const onRowEditCancel = (event) => {
  dialogRef.value.options.props.closable = true;
  const {data, newData, index} = event;
  if (data.id === null) {
    store.dsKH.shift();
    toast.add({severity: 'info', summary: 'Thông báo', detail: 'Huỷ bỏ thêm mới khách hàng', life: 3000});
  } else {
    store.dsKH[index] = newData;
    toast.add({severity: 'info', summary: 'Thông báo', detail: 'Huỷ bỏ chỉnh sửa', life: 3000});
  }
}

const addEmptyRowToTableKH = (event) => {
  dialogRef.value.options.props.closable = false;
  if (tableKHEditingRows.value.length === 0) {
    store.dsKH.unshift({id: null, ma: null, ten: '', sdt: ''});
    tableKHEditingRows.value = [store.dsKH[0]];
  } else toast.add({
    severity: 'error',
    summary: 'Không thể thực hiện',
    detail: 'Vui lòng hoàn tất chỉnh sửa',
    life: 3000
  });
}

const updateKHForHD = async () => {
  if (tableKHEditingRows.value.length > 0) toast.add({
    severity: 'error',
    summary: 'Không thể thực hiện',
    detail: 'Vui lòng hoàn tất chỉnh sửa',
    life: 3000
  });
  else if (selectedKhachHang.value === undefined) toast.add({
    severity: 'error',
    summary: 'Không thể thực hiện',
    detail: 'Vui lòng chọn khách hàng',
    life: 3000
  });
  else {
    await store.updateKHForHD(localStorage.getItem("selectedHDId"), selectedKhachHang.value.id);
    dialogRef.value.close({
      idUser: selectedKhachHang.value.id,
      user: selectedKhachHang.value
    });
    selectedKhachHang.value = null;
    toast.add({severity: 'success', summary: 'Thành công', detail: 'HĐ đã được cập nhật khách hàng', life: 3000});
  }
}
</script>

<template>
  <DataTable :value="dsKH" dataKey="id" editMode="row" v-model:selection="selectedKhachHang"
             v-model:filters="khachHangFilter" filterDisplay="menu" :globalFilterFields="['ten', 'sdt']" selectionMode="single"
             v-model:editingRows="tableKHEditingRows" @rowEditInit="onRowEditInit" @rowEditSave="onRowEditSave"
             @rowEditCancel="onRowEditCancel" showGridlines scrollable tableStyle="height: 50rem" scrollHeight="40rem">
    <template #header>
      <div class="flex justify-content-between align-items-center">
        <Button label="Thêm mới" icon="pi pi-user-plus" outlined @click="addEmptyRowToTableKH"/>
        <span class="p-input-icon-left">
          <i class="pi pi-search"/>
          <InputText :disabled="tableKHEditingRows.length > 0" v-model="khachHangFilter['global'].value" placeholder="Tìm kiếm khách hàng..."/>
        </span>
        <Button label="Chọn KH" icon="pi pi-arrow-circle-right" iconPos="right" severity="success" outlined
                @click="updateKHForHD"/>
        <!--          v-model="sanPhamFilter['global'].value"-->
      </div>
    </template>
    <template #footer v-if="tableKHEditingRows.length > 0">
      <InlineMessage class="w-full" severity="info">Note: Bạn không thể tắt hộp thoại này nếu chưa hoàn
        tất chỉnh sửa.
      </InlineMessage>
    </template>
    <Column header="Mã KH" field="ma" bodyClass="text-center"></Column>
    <Column header="Tên khách hàng" field="ten" class="w-16rem">
      <template #editor>
        <InputText v-model="tableKHEditingRows[0].ten" class="w-auto" :class="{ 'p-invalid': tenError }"/>
        <small class="p-error">{{ tenError }}</small>
      </template>
    </Column>
    <Column header="Số điện thoại" field="sdt" class="w-16rem">
      <template #editor>
        <InputText v-model="tableKHEditingRows[0].sdt" class="w-auto" :class="{ 'p-invalid': sdtError }"/>
        <br>
        <small class="p-error">{{ sdtError }}</small>
      </template>
    </Column>
    <Column :rowEditor="true" class="w-7rem text-center"></Column>
  </DataTable>
</template>

<style scoped></style>