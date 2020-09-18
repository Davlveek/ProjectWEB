import axios from 'axios'

axios.defaults.baseURL = '//localhost:8000/';

export default {
    // AUTH
    login(data) {
        return axios.post('/api/auth/login/', data);
    },
    refresh(data) {
        return axios.post('/api/auth/refresh/', data)
    },
    logout() {
        return axios.post('/api/auth/logout/', {})
    },
    signup(data) {
        return axios.post('/api/auth/signup/', data)
    },
    
    // GET/SET USER INFO
    getuserinfo() {
        return axios.get('/api/app/user/')
    },
    updateuserinfo(data) {
        return axios.post('/api/app/user/', data)
    },
    
    // QUEUE AND CHAT
    search(){
        return axios.get('/api/app/search/')
    },
    chat(){
        return axios.get('/api/app/chat/')
    }
};

export function updateHeader(token) {
        if (token === null) {
            axios.defaults.headers.common['Authorization'] = null;
        } else {
            axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
        }
}