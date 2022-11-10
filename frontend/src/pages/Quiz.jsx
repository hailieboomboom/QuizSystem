import React from 'react'
import QuizShortAnswerCard from "../components/QuizShortAnswerCard";
import Grid from "@mui/material/Grid";
import QuizMsqCard from "../components/QuizMsqCard";
import QuizMcqCard from "../components/QuizMcqCard";
import QuizHeaderCard from "../components/QuizHeaderCard";
import axios, * as others from 'axios';
import withStyles from "@mui/material/styles/withStyles";

const styles = theme => ({
    root: {
        flexGrow: 1,
    }
});

const Quiz = (props) => {
    const { classes } = props;

    const [quiz, setQuiz] = React.useState("");

    React.useEffect(() => {
        axios.get("https://the-trivia-api.com/api/questions?limit=10").then((response) => {
            setQuiz(response.data);


        });
    }, []);
    console.log(quiz);


    if (!quiz) return null;
    return (
        <div className={classes.root}>
            <h1>Quiz</h1>
            <Grid
                container
                direction="column"
                justifyContent="flex-start"
                alignItems="center"
            >
                <Grid item xs={6}>
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
                {/*{listQuestions}*/}

                <Grid item>
                    <QuizShortAnswerCard/>
                </Grid>
                <Grid item>
                    <QuizMsqCard/>
                </Grid>
                {/*<Grid item>*/}
                {/*    <QuizMcqCard/>*/}
                {/*</Grid>*/}

            </Grid>

        </div>
    )
};

export default withStyles(styles)(Quiz);

