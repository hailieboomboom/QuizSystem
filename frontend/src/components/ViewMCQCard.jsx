import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';

import '../styles/ViewQuestionsCard.css';
import QuestionData from '../data/QuestionData';

import axios from "axios";

const bull = (
    <Box
        component="span"
        sx={{ display: 'inline-block', mx: '2px', transform: 'scale(0.8)' }}
    >
    </Box>
);


export default function ViewMCQCard(props) {
    console.log(QuestionData)
    const [question, setQuestion] = React.useState([]);

    console.log(question)

    const changeStyle = () => {

    }

    return (
            <Card className={"questionCard"} sx={{ minWidth: 275, maxWidth: 500}}>
                <CardContent>
                    <div className={"questionCardContent"}>
                        <div>
                            <Typography className={"questionTitle"} color="text.secondary" gutterBottom>
                                {props.questionCard.question}
                            </Typography>
                            <Typography className={"questionString"} variant="h5" component="div">


                            </Typography>
                        </div>
                        <div className={"answerSection"}>
                            <button className={"correctAnswer"}>{props.questionCard.correctAnswer}</button>
                            <div className="divider"/>

                            {props.questionCard.incorrectAnswers.map((answer) => (<button className={"button"}>{answer}</button>))}

                        </div>
                    </div>


                </CardContent>
                <CardActions>
                    <Button size="small">Edit</Button>
                    <Button onClick={changeStyle} size="small">Show Answer</Button>
                </CardActions>
            </Card>

    );
}
