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
import questions from "../pages/Questions";
import {useState} from "react";
import Grid from "@mui/material/Grid";
import {Link, useNavigate} from "react-router-dom";
import {editQuestionOptionsState, editQuestionState} from "../recoil/Atoms";
import {useRecoilState} from "recoil";


const bull = (
    <Box
        component="span"
        sx={{ display: 'inline-block', mx: '2px', transform: 'scale(0.8)' }}
    >
    </Box>
);

export default function ViewMCQCard(props) {
    const [editQuestions, setEditQuestions] = useRecoilState(editQuestionState);
    const [answers, setAnswers] = useRecoilState(editQuestionOptionsState);
    const [question, setQuestion] = React.useState([]);

    const navigate = useNavigate();

    console.log("dasdad")
    console.log(question)

    const [show, setShow] = useState(false);

    function handleOnClick(){
        setEditQuestions(props.questionCard);
        setAnswers(props.questionCard.mcqOptionDtoList)
        navigate('/editQuestion')
    }

    function editQuestion() {
        const question = [
            props.questionCard
        ];
        console.log(question)
    }
    if(!setQuestion){
        return null;
    }
        return (

            <Card className={"questionCard"} sx={{ minWidth: 275, maxWidth: 500}}>
                <CardContent>
                    <div className={"questionCardContent"}>
                        <p className={"questionId"}>Question ID: {props.questionCard.questionId}</p>
                        <div>
                            <Typography className={"questionTitle"} color="text.secondary" gutterBottom>
                                {props.questionCard.questionDetail}
                            </Typography>
                            <Typography className={"questionString"} variant="h5" component="div"></Typography>
                        </div>

                            <Grid container spacing={1} direction="column"  >
                            {ShuffleAnswers(props.questionCard).map((shuffledAnswer, index) => (
                                <Grid item>
                                    <button value={shuffledAnswer.optionDescription} > {shuffledAnswer.optionDescription}</button>
                                </Grid>
                            ))}
                            </Grid>

                    </div>
                </CardContent>

                <Grid spacing={1} container direction="column" justifyContent="center" alignItems="center">

                    {show && <Grid item>
                    <p className={"resultAnswer"}>Answer:</p>
                    {props.questionCard.mcqOptionDtoList.map((option) => (
                        <p className={"resultAnswer"} hidden={!option.correct}> {option.optionDescription} </p>
                    ))}
                    </Grid>}
                    {/*{show && <Grid item>*/}



                    {/*    Correct Answer: {props.questionCard.correctAnswer}*/}
                    {/*</Grid>}*/}


                </Grid>
                <div className={"cardFooterButtons"}>
                    <CardActions>
                        {props.showEdit && <Button onClick={handleOnClick} size="small">Edit Question</Button>}
                        <Button size="small" onClick={()=>setShow(!show)}>{show === true ? 'Hide Answer' : 'Show Answer'}</Button>
                    </CardActions>
                </div>
            </Card>

    );
}
