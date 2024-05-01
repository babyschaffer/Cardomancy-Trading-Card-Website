export default {

    /**
     * Sorts a list of cards by alphabetical order. optional specification of whether sort should be ascending or descending
     *
     * @param {Card[]} list the list of cards to sort
     * @param {boolean} ascending defaults to true. Whether the sort is ascending or descending. True is ascending.
     * @returns {Card[]} sorted list
     */
    sortByName(list, ascending = true) {
        const compare = ascending ?
            ((card1, card2) => card1.name > card2.name)
            : ((card1, card2) => card1.name < card2.name);
        return selectionSort(list, compare);
    },

    /**
     * filters a list ton only include cards that match the name search query.
     *
     * @param {Card[]} list the list of cards to filter
     * @param {String} name the search query cards have to match
     * @param {boolean} caseSensitivity defaults to false. You can enable case sensitivity by settings this to true
     * @returns {Card[]} filtered list
     */
    filterByName(list, name, caseSensitivity = false) {
        if(caseSensitivity)
            return list.filter((card) =>
                card.name.includes(name));
        return list.filter((card) =>
        card.name.toLowerCase().includes(name.toLowerCase()));
    },

    /**
     * Sorts a list of cards by color. The order of colors is "WUBRG" with "U" being blue and "B" being black.
     * The more colors a card uses, the further down the list they show up
     *
     * @param {*} list the list of cards to sort
     * @returns {Card[]} sorted list
     */
    sortByColor(list) {
        const compare = (card1, card2) =>
            this.colorHash(card1.colors) >  this.colorHash(card2.colors);
        return selectionSort(list, compare);
    },

    /**
     * Filters a list to only include specified colors
     *
     * @param {Card[]} list the list of cards to be filtered
     * @param {Color[]} colors the list of colors cards could have
     * @returns {Card[]} filtered list
     */
    filterByColor(list, colors) {
        return list.filter((card) => {
            for (let i = 0; i < card.colors.length; i++) {
                const element = card.colors[i];
                if (!colors.contains(element))
                    return false;
            }
            return true;
        })
    },

    /**
     * Sorts a list of cards by converted mana cost. be default ascending. It will otherwise sort by descending.
     *
     * @param {Card[]} list the list of cards to be sorted.
     * @param {boolean} ascending defaults to true. Whether the sort is ascending or descending. True is ascending.
     * @returns {Card[]} sorted list.
     */
    sortByCmc(list, ascending = true) {
        const compare = ascending?
            (card1, card2) => card1.cmc > card2.cmc
            : (card1, card2) => card1.cmc < card2.cmc;
        return selectionSort(list, compare);
    },

    sortByColorId(list) {
        const compare = (card1, card2) => 
            this.colorHash(card1.colorIdentity) > this.colorHash(card2.colorIdentity);
        return selectionSort(list, compare);
    },
    
    sortBySetId(list) {
        const compare = (card1, card2) =>
            card1.setCode > card2.setCode;
        return selectionSort(list, compare);
    },

    sortByEdhrec(list) {
        const compare = (card1, card2) =>
            card1.edhrecRank > card2.edhrecRank;
        return selectionSort(list, compare);
    },

    /**
     * method to hash the color array in a way to make it easier to compare to other color arrays
     *
     * @param {Color[]} colors array to hash
     * @returns {Number} hash of colors
     */
    colorHash (colors) {
        let val = 0;

        val += colors.includes("W")? "1" : "";
        val += colors.includes("U")? "2" : "";
        val += colors.includes("B")? "3" : "";
        val += colors.includes("R")? "4" : "";
        val += colors.includes("G")? "5" : "";

        return Number(val);
    }

}

/**
 * Sorts an array by utilizing a comparison function
 *
 * @param {*[]} list is the array to be sorted
 * @param {boolean} compare is a function that may use the next element and the current element.
 * It should return true when the current element belongs behind the next element
 * @returns {Card[]} sorted List
 */
function selectionSort (list, compare) {
    for (let i = 0; i < list.length; i++) {
        const element = list[i];
        let j = i;
        while (j>0 && compare(list[j-1], element)){
            j--;
            list[j+1] = list[j];
        }
        list[j] = element;
    }
    return list;
}


/*
{
    "id": "aa1afaaa-70d5-434a-994d-cc341c8b8e29",
    "tcgId": 1,
    "name": "Solemn Simulacrum",
    "imageUrl": "https://cards.scryfall.io/normal/front/a/a/aa1afaaa-70d5-434a-994d-cc341c8b8e29.jpg?1674431279",
    "smallImgUrl": "https://cards.scryfall.io/small/front/a/a/aa1afaaa-70d5-434a-994d-cc341c8b8e29.jpg?1674431279",
    "scryfallUrl": "https://scryfall.com/card/brc/161/solemn-simulacrum?utm_source=api",
    "reverseImgUrl": "card_reverse_image_url",
    "smallReverseImgUrl": null,
    "colors": [
        ""
    ],
    "colorIdentity": [
        ""
    ],
    "setCode": "brc",
    "setName": "The Brothers' War Commander",
    "collectorNumber": "161",
    "legalities": {
        "standard": "not_legal",
        "commander": "legal",
        "modern": "legal",
        "oathbreaker": "legal",
        "vintage": "legal",
        "pioneer": "legal"
    },
    "layout": "normal",
    "cmc": 4.0,
    "edhrecRank": 30
}
*/