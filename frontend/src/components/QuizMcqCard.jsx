// Author: Istiaq

import * as React from 'react';
import "../styles/QuizCardStyle.css"
import FormControlLabel from "@mui/material/FormControlLabel";
import RadioGroup from "@mui/material/RadioGroup";
import Radio from "@mui/material/Radio";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import CardActions from "@mui/material/CardActions";
import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";
import axios from "axios";
import {useRecoilState} from "recoil";
import {quizSelectedAnswersState} from "../recoil/Atoms";
import {ShuffleTakeQuizAnswers} from "./ShuffleTakeQuizAnswer";
import CircularProgress from "@mui/material/CircularProgress";

export default function QuizMcqCard(props) {
    const [answers, setAnswers] = useRecoilState(quizSelectedAnswersState);
    const [value, setValue] = React.useState();
    const [options, setOptions] = React.useState([]);
    const [question, setQuestion] = React.useState();

    const handleChange = (event) => {
        setValue(event.target.value);
        if(answers){
        setAnswers((answers) =>
            answers.filter((answer) => answer.mcqId !== props.questionId)
        );}
        setAnswers(oldArray => [...oldArray,{
            "mcqId": props.questionId,
            "selectedOption": parseInt(event.target.value)
        }] );

    };
    console.log(answers);
    React.useEffect(() => {
        axios.get("http://localhost:8088/QuizSystem/api/questions/mcqs/"+props.questionId+"").then((response) => {
            setOptions(ShuffleTakeQuizAnswers(response.data.options));
            setQuestion(response.data);

            console.log(question)
        }).catch(function (error) {
            console.log(error);
        });
    }, []);

    if (!question) return (
        <Grid
            container
            justifyContent="center"
            alignItems="center"
        >
            <Grid item>
                <Typography>Please Wait...</Typography>
                <CircularProgress color="warning"/>
            </Grid>
        </Grid>
    );
    return (
        <Card className={"cardContent"} sx={{width: 700}}>
            <CardContent>
                <Typography className={"questionTitle"} color="text.secondary" gutterBottom>
                    Question:  {question.questionDetails}
                </Typography>
                <Typography color="text.secondary" gutterBottom>
                    Mark:  {props.grade}
                </Typography>
                <RadioGroup
                    className={"radioGroup"}
                    value={value}
                    onChange={handleChange}
                >
                    {options.map(answer => {
                        return <FormControlLabel key={answer.optionDescription} value={answer.id} control={<Radio color="default"/>} label={answer.optionDescription}/>;
                    })}
                </RadioGroup>
            </CardContent>
        </Card>


    );
}
