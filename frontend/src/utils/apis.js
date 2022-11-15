import axios from 'axios';
import { getCookie, getUserFlatRole } from './cookies';

export const apis = {
    signup,
    login,
    getUnauthorizedTrainers,
    authorizeTrainer,
    getUnauthorizedSales,
    authorizeSales
}

const config = {
    headers: { Authorization: `Bearer ${getCookie('token')}` }
}
const instance = axios.create({
    baseURL: 'http://localhost:8088/QuizSystem'
})


// function getUserById(id){
//     switch(getUserFlatRole()){
//         case "TRAINING":
//         case "POND":
//         case "BEACHED":
//             return instance.get("/users/students/" + id, config);
//         case "AUTHORISED_TRAINER":

//     }
// }

function getUnauthorizedTrainers(){
    return instance.get('/users/trainers/unauthorised' , config)
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

function signup(username, email, password, firstName, lastName,role){
    return instance.post('/auth/signup', {email, firstName, lastName, password, role, username})
}
function login(username, password) {
    return instance.post('/auth/login', {username, password})
}