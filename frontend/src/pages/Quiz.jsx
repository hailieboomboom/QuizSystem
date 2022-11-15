import React from 'react'
import QuizShortAnswerCard from "../components/QuizShortAnswerCard";
import Grid from "@mui/material/Grid";
import QuizMsqCard from "../components/QuizMsqCard";
import QuizMcqCard from "../components/QuizMcqCard";
import QuizHeaderCard from "../components/QuizHeaderCard";
import axios, * as others from 'axios';
import { useRecoilState } from 'recoil';
import {attemptQuizState, quizSelectedAnswersState} from '../recoil/Atoms'
import Button from "@mui/material/Button";
import {Link} from "react-router-dom";
// quizId

const Quiz = () => {

    const [answers, setAnswers] = useRecoilState(quizSelectedAnswersState);

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
        axios.post("http://localhost:8088/QuizSystem/api/quizAttempts", {
            "mcqattemptList": answers,
            "quizId": quiz.quizId,
            "userId": 1
        })
            .then(function (response) {
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
    return (
        <div>
            <h1>Quiz</h1>
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
                <Grid
                    item
                    direction="row"
                    justifyContent="space-between"
                    alignItems="flex-end" sx={{width: 700}}
                >
                    <Grid item/>
                    <Grid item>
                        <Button variant="outlined" size="large" onClick={handleSubmit} as={Link} to="/viewQuizzes">Submit</Button>
                    </Grid>

                </Grid>
                <Grid item/>
            </Grid>

        </div>
    )
};

export default Quiz;

