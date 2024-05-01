import axios from 'axios';

export default {
    isFriended(username) {
        return axios.get(`/isFriend/${username}`);
    },
    addFriend(username) {
        return axios.post(`/addFriend/${username}`);
    },
    unFriend(username) {
        return axios.delete(`/unFriend/${username}`);
    }
}