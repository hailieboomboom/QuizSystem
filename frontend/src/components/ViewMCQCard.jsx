import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';

import '../styles/ViewQuestionsCard.css';
import QuestionData from '../data/QuestionData';
import { ShuffleAnswers } from '../components/ShuffleAnswer';
import Answer from "../components/QuestionAnswer";

import axios from "axios";
import questions from "../pages/Questions";

const bull = (
    <Box
        component="span"
        sx={{ display: 'inline-block', mx: '2px', transform: 'scale(0.8)' }}
    >
    </Box>
);

export default function ViewMCQCard(props) {
    // const [question, setQuestion] = React.useState([]);


    const changeStyle = () => {

    }


    function correctAnswer(correctAnswer) {
        console.log(correctAnswer)
    }

    function changeColor() {
        const answer = [
            props.questionCard.correctAnswer,
        ];


        console.log(answer)

        // questionCard.correctAnswerforEach(correctAnswer)
    }

    return (
            <Card className={"questionCard"} sx={{ minWidth: 275, maxWidth: 500}}>
                <CardContent>
                    <div className={"questionCardContent"}>
                        <div>
                            <Typography className={"questionTitle"} color="text.secondary" gutterBottom>
                                {props.questionCard.question}
                            </Typography>
                            <Typography className={"questionString"} variant="h5" component="div"></Typography>
                        </div>
                        <div className={"answerSection"}>
                            {ShuffleAnswers(props.questionCard).map((shuffledAnswer, index) => (
                                <button value={shuffledAnswer}> {shuffledAnswer}</button>))
                            }

                        </div>
                    </div>

                </CardContent>

                <CardActions>
                    <Button size="small">Edit</Button>
                    <Button onClick={changeColor} size="small">Show Answer</Button>
                </CardActions>
            </Card>

    );
}
