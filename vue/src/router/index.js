import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import Collection from '../views/Collection.vue'
import CollectionDetails from "../views/CollectionDetails.vue"
import MyCollection from "../views/MyCollection.vue"
import NewCollection from "../views/NewCollection.vue"
import AddCardView from "../views/AddCardView.vue"
import SearchCardsView from "../views/SearchCardsView"
import Profile from "../views/Profile.vue"
import MessageTableView from "../views/MyMessagesView.vue"
import MessageView from "../views/SingleMessageView.vue"
import updateCollection from "../views/updateCollection.vue"
import RequestTrade from "../components/RequestTradeForm.vue"
import FAQ from "../components/FAQ.vue"
import AboutUsView from "../views/AboutUsView.vue"


Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/myCollections/",
      name: "myCollections",
      component: MyCollection,
      meta: {
        requiresAuth: true
      }
    },
    {
    path: "/allCollections",
    name: "allCollections",
    component: Collection,
    meta: {
      requiresAuth: false
    }
    },
    {
      path:"/collections/:id",
      name:"CollectionCards",
      component: CollectionDetails,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/collections/add",
      name: "collectionForm",
      component: NewCollection,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/collections/:id/add",
      name:"addCardToCollection",
      component: AddCardView,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/search-cards",
      name: "searchCards",
      component: SearchCardsView,
      meta: {
        requiresAuth: false
      }
    },
    { 
       path:"/FAQ",
      name:"FAQ",
      component: FAQ,
      meta: {
        requiresAuth: false
      }
    },
    {
      path:"/about",
      name:"About",
      component:AboutUsView,
      meta: {
        requiresAuth: false
      }
    },
    { 
       path:"/profile/:username",
      name:"Profile",
      component: Profile,
      meta: {
        requiresAuth: false
      }
    },
    { 
      path:"/updateCollection/:id",
     name:"edit-collection",
     component: updateCollection,
     meta: {
       requiresAuth: true
     }
   },
   {
    path:"/messages",
    name:"messages",
    component: MessageTableView,
    meta: {
      requiresAuth: true
    }
   },

   {
    path:"/messages/:id",
    name:"messagesByID",
    component: MessageView,
    
  },
  {
    path:"/user/:username/request-trade",
    name:"request-trade-form",
    component: RequestTrade,
    meta: {
      requiresAuth: true
    }
  }

  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
