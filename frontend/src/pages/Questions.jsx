import React from 'react'
import Typography from '@mui/material/Typography';
import ViewMCQCard from "../components/ViewMCQCard";
import '../styles/ViewQuestionsCard.css';
import {apis} from "../utils/apis"
import { getUserId, getUserRole } from '../utils/cookies';
import { useState } from 'react';

const Questions = () => {

    const [question, setQuestion] = useState([]);
    const [showEdit, setShowEdit] = useState(true);
    const [role, setRole] = useState("");

    React.useEffect(() => {
        const role = getUserRole();
        setRole(role);
        if(role === "TRAINING" || role === "POND" || role === "BEACHED" || role === "AUTHORISED_SALES") {
            setShowEdit(false);
        }
        getAllMCQs();
    }, []);

    // const getAllMCQs = () => {
    //     console.log("first")
    //     const url = "http://localhost:8088/QuizSystem/api/questions/mcqs";
    //     axios.get(url).then((response) => {
    //         setQuestion(response.data);
    //         console.log("hello")
    //         console.log(response.data)
    //         console.log(question);
    //     });
    // }

    const getAllMCQs = () => {
        apis.getAllMCQs().then(
            res => {
                setQuestion(res.data);
                if(role === "TRAINING" || role === "POND" || role === "BEACHED" || role === "AUTHORISED_SALES") {
                    setShowEdit(false);
                }
                else if(role === "AUTHORISED_TRAINER") {
                    setShowEdit(true);
                }
                }
        ).catch(
            err => console.log(err)
        )
    }

    const getAllMCQsFromUser = () => {
        apis.getAllMCQsFromUser(getUserId()).then(
            res => {
                setQuestion(res.data);
                setShowEdit(true);
            }
        ).catch(
            err => console.log(err)
        )
    }

    const getAllInterviewQuestions = () => {
        apis.getAllInterviewQuestions().then(
            res => {
                setQuestion(res.data);
                if(role === "TRAINING" || role === "POND" || role === "BEACHED") {
                    setShowEdit(false);
                }
                else if(role === "AUTHORISED_TRAINER" || role === "AUTHORISED_SALES") {
                    setShowEdit(true);
                }
            }
        ).catch(
            err => console.log(err)
        )
    }

    const getAllCourseQuestions = () => {
        apis.getAllCourseQuestions().then(
            res => {
                setQuestion(res.data);
                if(role === "TRAINING" || role === "POND" || role === "BEACHED" || role === "AUTHORISED_SALES") {
                    setShowEdit(false);
                }
                else if(role === "AUTHORISED_TRAINER") {
                    setShowEdit(true);
                }
            }
        ).catch(
            err => console.log(err)
        )
    }

    // const getAllMCQsFromUser = () => {
    //     console.log("first")
    //     const url = "http://localhost:8088/QuizSystem/api/questions/mcqs";
    //     axios.get(url).then((response) => {
    //         setQuestion(response.data);
    //         console.log("hello")
    //         console.log(response.data)
    //         console.log(question);
    //     });
    // }

    return (

        <div className={"mcqCardContainer"}>
            <div className={"filterQuestionButton"}>
                <button onClick={getAllMCQs}>All Questions</button>
                <button onClick={getAllCourseQuestions}>Course Questions</button>
                <button onClick={getAllInterviewQuestions}>Interview Questions</button>
                <button onClick={getAllMCQsFromUser}>Your Questions</button>
            </div>
            <div className={"viewQuestionsBox"}>
                <h1 className={"questionListTitle"}>Multiple Choice Questions</h1>
                {
                    question.map((question) => (<ViewMCQCard questionCard={question} showEdit={showEdit}/>))
                }
            </div>

        </div>

    )


}
export default Questions