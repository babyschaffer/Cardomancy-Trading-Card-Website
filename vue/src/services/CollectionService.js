import axios from 'axios';

export default {
    getMyCollections() {
        return axios.get('/myCollections');
    },
    getAllCollections() {
        return axios.get("/allCollections");
    },
    addCollection(collection) {
        return axios.post("myCollections/add", collection);
    },
    deleteCollection(collectionId){
        return axios.delete(`/myCollections/${collectionId}`);
    },
    getAllCardsByCollection(collectionId){
        return axios.get(`/collections/${collectionId}/cards`);
    },
    getCollectionById(collectionId){
        return axios.get(`collections/${collectionId}`)
    },
    getUserForCollectionId(collectionId){
        return axios.get(`/collections/${collectionId}/user`);
    },
    addCardToCollection(collectionId, card){
        return axios.post(`/myCollections/${collectionId}/add`, card);
    },
    deleteCardFromCollection(cardId, collectionId){
        return axios.delete(`/collections/${collectionId}/${cardId}/delete`);
    },

    getUserCollections(username){
        return axios.get(`/userCollections/${username}`);
    },


// sorted lists for collections display

    getAllCardsByCollectionAlph(collectionId){
        return axios.get(`/collections/${collectionId}/cards/a`);
    },
    getAllCardsByCollectionColor(collectionId){
        return axios.get(`/collections/${collectionId}/cards/c`);
    },
    getAllCardsByCollectionColorIdentity(collectionId){
        return axios.get(`/collections/${collectionId}/cards/ci`);
    },
    getAllCardsByCollectionSet(collectionId){
        return axios.get(`/collections/${collectionId}/cards/s`);
    },
    getAllCardsByCollectionLegality(collectionId){
        return axios.get(`/collections/${collectionId}/cards/l`);
    },
    getAllCardsByCollectionCMC(collectionId){
        return axios.get(`/collections/${collectionId}/cards/cmc`);
    },
    getAllCardsByCollectionEDHREC(collectionId){
        return axios.get(`/collections/${collectionId}/cards/ed`);
    },
    getCountOfCardsInCollection(collectionId) {
        return axios.get(`/collections/allCollections/${collectionId}/stats`)
    },
    updateCollection(collectionId, collection) {
        return axios.put(`/collections/${collectionId}/update`, collection);
    }
}