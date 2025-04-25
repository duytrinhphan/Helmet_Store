<script setup>
import {computed, onBeforeMount, onMounted,ref} from "vue";
import {register, VueAdvancedChat} from "vue-advanced-chat";
import {useChatStore} from "@/service/Admin/Chat/ChatService";
import {verifyJwt} from "@/service/common/JwtUtils";


const store = useChatStore();
const rooms = computed(() => store.rooms);
const roomsLoaded = computed(() => store.roomsLoaded);
const loadingRooms = computed(() => store.loadingRooms);
const messages = computed(() => store.messages);
const messagesLoaded = computed(() => store.messagesLoaded);
const currentUsername = ref(null);

register();

const customTheme = {
  container: {
    borderRadius: '10px',
  }
}

const customTextMessage = {
  ROOM_EMPTY: 'Loading...',
  NEW_MESSAGES: 'Tin nhắn mới',
  MESSAGES_EMPTY: 'Không có tin nhắn',
  CONVERSATION_STARTED: 'Cuộc trò chuyện bắt đầu vào:',
  TYPE_MESSAGE: 'Nhập tin nhắn',
  SEARCH: 'Tìm kiếm',
  IS_ONLINE: 'Đang hoạt động',
  LAST_SEEN: 'Hoạt động ',
  IS_TYPING: 'Đang nhập...',
  CANCEL_SELECT_MESSAGE: 'Bỏ chọn'
}

const props = defineProps({
  height: String,
  singleRoom: Boolean,
});

const fetchMessages = async (event) => {
  //console.log("log");
  await store.fetchMessages(event);
}

const sendMessages = async (event) => {
  await store.sendMessage(event);
}

onBeforeMount(async () => {
 
  const token = localStorage.getItem('token');
  
  if (token) {
  const payloadData = await verifyJwt(token);
  currentUsername.value = payloadData.sub;
  await store.setup(payloadData.sub, payloadData.id);

}
 
});


</script>

<template>
  <vue-advanced-chat :height="height"
                     :current-user-id="currentUsername"
                     theme="light"
                     :styles="JSON.stringify(customTheme)"
                     :text-messages="JSON.stringify(customTextMessage)"
                     :loading-rooms="loadingRooms"
                     :rooms-loaded="roomsLoaded"
                     :messages-loaded="messagesLoaded"
                     :single-room="singleRoom"
                     :show-search="false"
                     :show-add-room="false"
                     :show-files="false"
                     :show-audio="false"
                      :show-emojis="false"
                     :show-reaction-emojis="false"
                   
                     .messages="messages"  .rooms="rooms"
                    
                     @fetch-messages="fetchMessages($event.detail[0])"
                     @send-message="sendMessages($event.detail[0])"/>
</template>


<!--    -->
<style scoped></style>