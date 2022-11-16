import * as React from 'react';
import Typography from '@mui/material/Typography';
import "../styles/QuizCardStyle.css"
import { useRecoilState } from 'recoil';
import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import TableBody from "@mui/material/TableBody";
import TableContainer from "@mui/material/TableContainer";
import Button from "@mui/material/Button";
import {createQuizAllQuestions, createQuizSelectedQuestions, createQuestionOptionsState} from '../recoil/Atoms'
import TextField from "@mui/material/TextField";
import QuizSelectedQuestionsTableRow from "./QuizSelectedQuestionsTableRow";
import FormControl from '@mui/material/FormControl';


export default function CreateWrongOptions(props) {
    const [answer, setAnswer] = React.useState(props.option);
    const [answers, setAnswers] = useRecoilState(createQuestionOptionsState);//from backend

    const handleChange = (event) => {
        setAnswer(event.target.value);
        props.handleChildChange(answer,props.option.index)


    };

    React.useEffect(()=> {
        //here you will have correct value in userInput
        // setAnswers((answer) =>
        //     answer.filter((answer) => answer.id !== props.option.id)
        // );
        // setAnswers(oldArray => [{
        //     "correct": props.option.correct,
        //     "id": props.option.id,
        //     "optionDescription": answer
        // },...oldArray] );
    },[]);

    return (
        <FormControl variant="standard"  fullWidth>
        <TextField
            required
            id={props.option.id}
            name="questionString"
            label="Incorrect Answer"
            fullWidth
            variant="standard"
            value={answer.optionDescription}
            onChange={handleChange}
        > {props.option.optionDescription} </TextField>

        <Button variant="outlined" >
            Delete
        </Button>
        </FormControl>
    );
}
