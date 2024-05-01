import axios from "axios";

export default{
    getMessagesForUser (){
        return axios.get('/messages/myMessages');
    },
    getSingleMessageByID(messageID){
        return axios.get(`/messages/${messageID}`)
    },

    sendNewMessage(messageToSend){
        return axios.post('/messages/new-message', messageToSend);
    },

    deleteMessage(messageID){
        return axios.delete(`/messages/${messageID}`);
    },

    updateReadStatus(messageID){
        return axios.put(`/messages/${messageID}`)
    }
    
}