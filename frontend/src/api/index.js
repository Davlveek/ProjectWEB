import axios from 'axios'

const session = axios.create({});

export default {
    login(email, password) {
        return session.post('/api/auth/login/', {email, password});
    },
    logout() {
        return session.post('/api/auth/logout/', {})
    },
    signup(data) {
        return session.post('/api/auth/signup/', data)
    },
    updateInfo(data) {
        return session.post('/api/app/updateinfo/', data)
    },
    pickup(){
        return session.get('/api/app/pickup/')
    }
};