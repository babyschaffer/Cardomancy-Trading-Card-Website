import axios from "axios";

export default{
    getMyProfile(){
        return axios.get('/myProfile')
    },

    getAProfile(username){
        return axios.get(`/profile/${username}`)
    },

    searchManyProfile(searchParameter){
        return axios.get ("/search/users",searchParameter)
    }



}