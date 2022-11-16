import axios from 'axios';
import { getCookie, getUserRole} from './cookies';

export const apis = {
    signup,
    login,
    getUnauthorizedTrainers,
    authorizeTrainer,
    getUnauthorizedSales,
    authorizeSales,
    isLoggedIn,
    getUserById,
    updateStudentInfo,
    updateTrainerInfo,
    updateSalesInfo,
    getRoleByUserId
}

const config = {
    headers: { Authorization: `Bearer ${getCookie('token')}` }
}
const instance = axios.create({
    baseURL: 'http://localhost:8088/QuizSystem'
})


function getUserById(id){
    switch(getUserRole()){
        case "TRAINING":
        case "POND":
        case "BEACHED":
            return instance.get("/users/students/" + id, config);
        case "AUTHORISED_TRAINER":
            return instance.get("/users/trainers/" + id, config);
        case "AUTHORISED_SALES":
            return instance.get("/users/sales/" + id, config);
    }
}

function updateStudentInfo(id, password, email, firstName, lastName){
    return instance.put("/users/students/" + id, {password, email, firstName, lastName}, config);
}

function updateTrainerInfo(id, password, email, firstName, lastName){
    return instance.put("/users/trainers/" + id, {password, email, firstName, lastName}, config);
}

function updateSalesInfo(id, password, email, firstName, lastName){
    return instance.put("/users/sales/" + id, {password, email, firstName, lastName}, config);
}

function getUnauthorizedTrainers(){
    return instance.get('/users/trainers/unauthorised' , config)
}

function isLoggedIn(){
    return getCookie('token');
}

function getUnauthorizedSales(){
    return instance.get('/users/sales/unauthorised' , config)
}
function authorizeTrainer(username){
    return instance.put("/users/trainers/authorise/"+ username, {}, config)
}

function authorizeSales(username){
    return instance.put("/users/sales/authorise/"+ username, {}, config)
}

function getRoleByUserId(id) {
    return instance.get("/users/" + id + "/role", config)
}

function signup(username, email, password, firstName, lastName,role){
    return instance.post('/auth/signup', {email, firstName, lastName, password, role, username})
}
function login(username, password) {
    return instance.post('/auth/login', {username, password})
}