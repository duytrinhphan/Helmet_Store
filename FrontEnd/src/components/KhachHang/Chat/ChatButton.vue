<script setup>
import ChatComponent from "@/components/Admin/Chat/ChatComponent.vue";
import {ref} from "vue";
import {onClickOutside} from "@vueuse/core";

const messageBox = ref(null);
const isChatPanelDisplayed = ref(false);

onClickOutside(messageBox, evt => {isChatPanelDisplayed.value = false;});
</script>

<template>
  <div class="chat-panel" :class="isChatPanelDisplayed ? 'w-26rem' : null">
    <div class="message-box" ref="messageBox">
            <ChatComponent height="100%" :single-room="true"/>
    </div>
    <Button label="CSKH" rounded severity="help" class="chat-button w-5rem h-5rem" iconPos="top"
      @click="isChatPanelDisplayed = !isChatPanelDisplayed">
      <template #icon>
        <i class="text-4xl" :class="isChatPanelDisplayed ? 'pi pi-times' : 'pi pi-comments'" />
      </template>
    </Button>
  </div>
</template>

<style scoped>
.chat-panel {
  position: fixed;
  right: 3%;
  bottom: 6%;
  height: 50%;
}

.message-box {
  position: absolute;
  height: 90%;
  width: 90%;
  z-index: 0;
}

.chat-button {
  position: absolute;
  right: 0;
  bottom: 0;
  z-index: 1;
}
</style>