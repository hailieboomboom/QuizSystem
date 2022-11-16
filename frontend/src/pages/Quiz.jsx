import React from 'react'
import QuizShortAnswerCard from "../components/QuizShortAnswerCard";
import Grid from "@mui/material/Grid";
import QuizMsqCard from "../components/QuizMsqCard";
import QuizMcqCard from "../components/QuizMcqCard";
import QuizHeaderCard from "../components/QuizHeaderCard";
import "../styles/QuizCardStyle.css"
import axios, * as others from 'axios';
import { useRecoilState } from 'recoil';
import {attemptQuizState, quizSelectedAnswersState} from '../recoil/Atoms'
import Button from "@mui/material/Button";
import {Link} from "react-router-dom";
import {getUserId} from "../utils/cookies";
import Typography from "@mui/material/Typography";
// quizId

const Quiz = () => {

    const [answers, setAnswers] = useRecoilState(quizSelectedAnswersState);
    const [grade, setGrade] = React.useState();


    React.useEffect(() => {
        const url = "http://localhost:8088/QuizSystem/api/quizzes/" + quiz.quizId + "/questions";
        axios.get(url).then((response) => {
            setQuizQuestions(response.data);
            console.log(quizQuestions);
            // setQuiz(response.data);
        });
    }, []);

    const [quizQuestions, setQuizQuestions] = React.useState([]);
    const [quiz, setQuiz] = useRecoilState(attemptQuizState);

    const submitQuiz = () => {
        axios.post("http://localhost:8088/QuizSystem/api/quizAttempts/" + getUserId() + "", {
            "mcqattemptList": answers,
            "quizId": quiz.quizId,
            "userId": getUserId()
        })
            .then(function (response) {
                setGrade(response.data)
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            });
    }
    const handleSubmit = () =>{
        submitQuiz();
    }

    if (!quizQuestions) return null;
    if (grade) return (
        <Grid className={"quizContainer"}>
            <Typography color="warning">Thank you ! Your quiz attempt has been submitted. </Typography>
            <Typography> You have gained {grade.totalAwarded} marks out of {grade.maxGrade}
            </Typography>
        </Grid>
    );
    return (
        <div className={"quizContainer"}>
            <Grid

                container
                direction="column"
                justifyContent="flex-start"
                alignItems="center"
                spacing={3}
            >
                <Grid item>
                    <QuizHeaderCard quizName={quiz.name} />
                </Grid>

                {
                    quizQuestions.map((questions) =>

                            <Grid item>
                                <QuizMcqCard
                                    key={questions.questionId}
                                    questionId={questions.questionId}
                                    grade={questions.grade}
                                />
                            </Grid>
                    )
                }

                    <Grid item>
                        <Button fullWidth className={"submitButton"} onClick={handleSubmit} >Submit</Button>
                    </Grid>

                <Grid item/>
            </Grid>

        </div>
    )
};

export default Quiz;

