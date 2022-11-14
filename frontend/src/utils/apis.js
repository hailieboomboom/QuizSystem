import axios from 'axios';
import { getCookie } from './cookies';

export const apis = {
    signup,
    login
}

const config = {
    headers: { Authorization: `Bearer ${getCookie('token')}` }
}
const instance = axios.create({
    baseURL: 'http://localhost:8088/QuizSystem'
})

function signup(username, email, password, firstName, lastName,role){
    return instance.post('/auth/signup', {email, firstName, lastName, password, role, username})
}
function login(username, password) {
    return instance.post('/auth/login', {username, password})
}