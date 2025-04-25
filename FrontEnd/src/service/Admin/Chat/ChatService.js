import {defineStore} from "pinia";
import {ref} from "vue";
import axios from '@/service/Authentication/http.js';
import * as chatService from "@/service/chatkitty";
import {ChatKitty} from 'chatkitty-platform-sdk'

const API_ENDPOINT = `${import.meta.env.VITE_BASE_API_ENDPOINT}/admin/chat`;
const chatkitty = new ChatKitty({
    clientId: import.meta.env.VITE_CHATKITTY_APIKEY,
    clientSecret: import.meta.env.VITE_CHATKITTY_SECRET
});

export const useChatStore = defineStore('chat', () => {
    const dsUser = ref();
    const rooms = ref([]);
    const roomsLoaded = ref(false);
    const loadingRooms = ref(false);
    const messages = ref([]);
    const messagesLoaded = ref(false);
    const currentRoom = ref(null);
    const currentUsername = ref();

    const loadUserFromBackend = async () => {
        const response = await axios.get(`${API_ENDPOINT}/user`);
        dsUser.value = response.data;
    }

    const createChatkittyUser = async (name, displayName) => {
        const user = await chatkitty.Users.createUser({
            name,
            displayName,
            isGuest: false
        });
      //  console.log(user);
    }

    const createDirectChannel = async (username) => {
        const channel = await chatkitty.Channels.createChannel({
            type: 'DIRECT',
            members: [
                { username },
                { username: 'cskh@gmail.com' }
            ]
        });
    //    console.log(channel);
    }

    const setup = async (username,id) => {
       await chatService.login(username,id);
        loadingRooms.value = true;
        rooms.value = await chatService.fetchRooms();
        loadingRooms.value = false;
        roomsLoaded.value = true;
    };

    const fetchMessages = async ({ room, options = {} }) => {
      //  console.log("fetching messages");
        if (options.reset) {
            chatService.exitRoom(room);
            messages.value = [];
            messagesLoaded.value = false;
            await chatService.enterRoom({
                room,
                onMessageReceived: (message) => {
                    messages.value = [...messages.value, message];
                },
                onRoomUpdated: (room) => {
                    currentRoom.value = room;
                    rooms.value = rooms.value.map((r) => (r.roomId === room.roomId ? room : r));
                }
            });
            currentRoom.value = room;
        }
        if (messagesLoaded.value) {
            return;
        }
        const { items, hasMore } = await chatService.fetchMessages(room);
        messages.value = [...items, ...messages.value];
        messagesLoaded.value = !hasMore;
    };

    const sendMessage = ({ content }) => {
        chatService.sendMessage({ room: currentRoom.value, content });
    };

    const tearDown = async () => {
        await chatService.logout();
        rooms.value = [];
        roomsLoaded.value = false;
        loadingRooms.value = false;
        messages.value = [];
        messagesLoaded.value = false;
    };

    return {
        dsUser,
        rooms,
        roomsLoaded,
        loadingRooms,
        messages,
        messagesLoaded,
        currentRoom,
        currentUsername,
        loadUserFromBackend,
        createChatkittyUser,
        createDirectChannel,
        setup,
        fetchMessages,
        sendMessage,
        tearDown
    }
});