import axios from 'axios'

axios.defaults.baseURL = 'https://rev-o-dates.social:8000/';

axios.interceptors.request.use(function (config) {
    let access_token = localStorage.getItem('access_token');
    if (access_token) {
        config.headers.Authorization = 'Bearer ' + access_token;
    }
    return config;
}, function (error) {
    return Promise.reject(error);
});

axios.interceptors.response.use((response) => {
    return response;
}, async function (error) {
    if ((error.response.config.url !== '/api/auth/login/') && (error.response.status === 401)) {
        const refresh_token = localStorage.getItem('refresh_token');

        await api.refresh({refresh: refresh_token})
            .then(({data}) => {
                localStorage.setItem('access_token', data.access);
            })
            .catch(() => {
                localStorage.removeItem('access_token');
                localStorage.removeItem('refresh_token');
            })
    }
    return Promise.reject(error.response);
});

const api = {
    // AUTH
    login(data) {
        return axios.post('/api/auth/login/', data);
    },
    refresh(data) {
        return axios.post('/api/auth/refresh/', data)
    },
    signup(data) {
        return axios.post('/api/auth/signup/', data)
    },
    
    // GET/SET USER INFO
    getuserinfo() {
        return axios.get('/api/app/user/')
    },
    updateuserinfo(data) {
        return axios.patch('/api/app/user/', data)
    },

};

export { api as default};
