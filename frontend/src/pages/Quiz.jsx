import React from 'react'
import QuizShortAnswerCard from "../components/QuizShortAnswerCard";
import Grid from "@mui/material/Grid";
import QuizMsqCard from "../components/QuizMsqCard";
import QuizMcqCard from "../components/QuizMcqCard";
import QuizHeaderCard from "../components/QuizHeaderCard";
import axios, * as others from 'axios';
import { useRecoilState } from 'recoil';
import { attemptQuizState } from '../recoil/Atoms'
import Button from "@mui/material/Button";
// quizId

const Quiz = () => {

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

    //
    //
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
                    <QuizHeaderCard/>
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
                    <QuizShortAnswerCard/>
                </Grid>
                <Grid item>
                    <QuizMsqCard/>
                </Grid>
                <Grid
                    item
                    direction="row"
                    justifyContent="space-between"
                    alignItems="flex-end" sx={{width: 700}}
                >
                    <Grid item/>
                    <Grid item>
                        <Button variant="outlined" size="large">Submit</Button>
                    </Grid>

                </Grid>
                <Grid item/>
                {/*<Grid item>*/}
                {/*    <QuizMcqCard/>*/}
                {/*</Grid>*/}

            </Grid>

        </div>
    )
};

export default Quiz;

