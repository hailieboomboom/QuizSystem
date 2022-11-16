import React from 'react'
import Typography from '@mui/material/Typography';
import ViewMCQCard from "../components/ViewMCQCard";
import '../styles/ViewQuestionsCard.css';
import Grid from "@mui/material/Grid";
import QuestionData from '../data/QuestionData';
import axios from "axios";
import {apis} from "../utils/apis"
import { getUserId } from '../utils/cookies';

const Questions = () => {
    const [question, setQuestion] = React.useState([]);

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
            }
        ).catch(
            err => console.log(err)
        )
    }

    const getAllMCQsFromUser = () => {
        apis.getAllMCQsFromUser(getUserId()).then(
            res => {
                setQuestion(res.data);
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

    React.useEffect(() => {
        getAllMCQs()
    }, []);

    return (

        <div className={"mcqCardContainer"}>
            <div className={"filterQuestionButton"}>
                <button onClick={getAllMCQsFromUser}>Your Questions</button>
                <button onClick={getAllMCQs}>All Questions</button>
            </div>
            <div className={"viewQuestionsBox"}>
                <h1 className={"questionListTitle"}>Multiple Choice Questions</h1>
                {
                    question.map((question) => (<ViewMCQCard questionCard={question}/>))
                }
            </div>

        </div>

    )


}
export default Questions