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


const Quiz = () => {

    // const [quiz, setQuiz] = React.useState("");
    const [quiz, setQuiz] = useRecoilState(attemptQuizState);
    console.log(quiz);
    console.log("asb");

    // React.useEffect(() => {
    //     axios.get("https://the-trivia-api.com/api/questions?limit=10").then((response) => {
    //         setQuiz(response.data);
    //     });
    // }, []);
    // console.log(quiz);
    //
    //
    // if (!quiz) return null;
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
                    quiz.map((questions) =>
                        <Grid item>
                            <QuizMcqCard
                                key={questions.id}
                                question={questions.question}
                                rightAnswer={questions.correctAnswer}
                                wrongAnswers={questions.incorrectAnswers}
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

