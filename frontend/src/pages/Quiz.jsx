import React from 'react'
import QuizShortAnswerCard from "../components/QuizShortAnswerCard";
import Grid from "@mui/material/Grid";
import QuizMsqCard from "../components/QuizMsqCard";
import QuizMcqCard from "../components/QuizMcqCard";
import QuizHeaderCard from "../components/QuizHeaderCard";

const Quiz = () => {
    return (
        <div>
            <h1>Quiz</h1>
            <Grid
                container
                direction="column"
                justifyContent="flex-start"
                alignItems="center"
            >
                <Grid item>
                    <QuizHeaderCard/>
                </Grid>
                <Grid item>
                    <QuizShortAnswerCard/>
                </Grid>
                <Grid item>
                    <QuizMsqCard/>
                </Grid>
                <Grid item>
                    <QuizMcqCard/>
                </Grid>

            </Grid>

        </div>
    )
}

export default Quiz
