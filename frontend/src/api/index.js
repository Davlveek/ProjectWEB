import axios from 'axios'

axios.defaults.baseURL = 'https://rev-o-dates.social:8000/';

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
    
    // GET INFO
    pickup(){
        return axios.get('/api/app/pickup/')
    },
    getuserinfo() {
        console.log(axios.defaults.headers)
        return axios.get('/api/app/user/')
    },

    // SET INFO
    updateuserinfo(data) {
        return axios.post('/api/app/user/', data)
    },
};

export function updateHeader(token) {
        if (token === null) {
            axios.defaults.headers.common['Authorization'] = null;
        } else {
            axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
        }
}
