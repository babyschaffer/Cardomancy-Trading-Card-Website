import axios from 'axios';

export default {
    getAllCards() {
        return axios.get("/search-cards");
   },

   getAllCardsNotLoggedIn(){
        return axios.get("/search-cards/free") 
   },
   getMultipleCardsByTitle(search, exactMatch) {
       return axios.get("/cards/search/title", search, exactMatch);
   }

}