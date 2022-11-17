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
    getRoleByUserId,
    getAllStudents,
    getAllMCQs,
    getAllMCQsFromUser,
    updateStudentInfoWithRole,
    getAllInterviewQuestions,
    getAllCourseQuestions
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

function getAllStudents(){
    return instance.get("/users/students")
}

function updateStudentInfo(id, password, email, firstName, lastName){
    return instance.put("/users/students/" + id, {password, email, firstName, lastName}, config);
}
 
function updateStudentInfoWithRole(id, password, email, firstName, lastName, role) {
    return instance.put("/users/students/" + id, {password, email, firstName, lastName, role}, config);
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

function getAllMCQs() {
    return instance.get("/api/questions/mcqs", config)
}

function getAllMCQsFromUser(id){
    return instance.get("/api/questions/" + id + "/mcqs", config)
}

function getAllInterviewQuestions(){
    return instance.get("/api/questions/questionBank/interview", config)
}

function getAllCourseQuestions(){
    return instance.get("/api/questions/questionBank/course", config)
}

function signup(username, email, password, firstName, lastName, role){
    return instance.post('/auth/signup', {username, email, firstName, lastName, password, role})
}
function login(username, password) {
    return instance.post('/auth/login', {username, password})
}