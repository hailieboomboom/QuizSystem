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
import {useState} from "react";
import Grid from "@mui/material/Grid";
import {Link} from "react-router-dom";
import {editQuestionState} from "../recoil/Atoms";
import {useRecoilState} from "recoil";


const bull = (
    <Box
        component="span"
        sx={{ display: 'inline-block', mx: '2px', transform: 'scale(0.8)' }}
    >
    </Box>
);

export default function ViewMCQCard(props) {
    const [editQuestions, setEditQuestions] = useRecoilState(editQuestionState)
    const [question, setQuestion] = React.useState([]);


    console.log("dasdad")
    console.log(question)

    const [show, setShow] = useState(false);
    const changeStyle = () => {

    }

    function handleOnClick(){
        setEditQuestions(props.questionCard)
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
                        <div>
                            <Typography className={"questionTitle"} color="text.secondary" gutterBottom>
                                {props.questionCard.questionDetail}
                            </Typography>
                            <Typography className={"questionString"} variant="h5" component="div"></Typography>
                        </div>

                            {/*<Grid container spacing={1} direction="column"  >*/}
                            {/*{ShuffleAnswers(props.questionCard).map((shuffledAnswer, index) => (*/}
                            {/*    <Grid item>*/}
                            {/*        <button value={shuffledAnswer} > {shuffledAnswer}</button>*/}

                            {/*    </Grid>*/}
                            {/*))}*/}
                            {/*</Grid>*/}

                        <Grid container spacing={1} direction="column"  >

                            {props.questionCard.mcqOptionDtoList.map((option) => (
                                <button> {option.optionDescription} </button>
                            ))}

                        </Grid>

                    </div>
                </CardContent>

                <Grid spacing={1} container direction="column" justifyContent="center" alignItems="center">

                    {/*{show && <Grid item>*/}
                    {/*<span className="checkmark"><div className="checkmark_circle"></div><div className="checkmark_stem"></div><div className="checkmark_kick"></div></span>*/}
                    {/*</Grid>}*/}
                    {show && <Grid item>
                        Correct Answer: {props.questionCard.correctAnswer}
                    </Grid>}


                </Grid>
                <CardActions>
                    <Button onClick={handleOnClick} as={Link} to="/editQuestion" size="small">Edit</Button>
                    <Button size="small" onClick={()=>setShow(!show)}>{show === true ? 'Hide Answer' : 'Show Answer'}</Button>

                </CardActions>
            </Card>

    );
}
