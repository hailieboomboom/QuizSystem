import * as React from 'react';
import Typography from '@mui/material/Typography';
import "../styles/QuizCardStyle.css"
import { useRecoilState } from 'recoil';
import "../styles/QuizCardStyle.css"
import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import TableBody from "@mui/material/TableBody";
import TableContainer from "@mui/material/TableContainer";
import Button from "@mui/material/Button";
import { createQuizAllQuestions, createQuizSelectedQuestions } from '../recoil/Atoms'
import TextField from "@mui/material/TextField";
import Chip from "@mui/material/Chip";

export default function QuizAllQuestionsTableRow(props) {
    const [quizAllQuestions, setquizAllQuestions] = useRecoilState(createQuizAllQuestions);
    const [quizSelectedQuestions, setquizSelectQuestions] = useRecoilState(createQuizSelectedQuestions);
    const [grade, setGrade] = React.useState(props.question.grade);

    const handleChange = (event) => {
        setGrade(event.target.value);
    };
    function handleAdd (current) {
        setquizAllQuestions((questions) =>
            questions.filter((question) => question.questionId !== current.questionId)
        );
        setquizSelectQuestions(oldArray => [...oldArray,{
            "questionId": props.question.questionId,
            "grade": grade,
            "questionDetails": props.question.questionDetails,
            "tags": props.question.tags
        }] );
        setGrade(props.question.grade)
    }

    function handleRemove (current) {
        setquizSelectQuestions((questions) =>
            questions.filter((question) => question.questionId !== current.questionId)
        );
        setquizAllQuestions([...quizAllQuestions,current]);
    }
    return (
        <TableRow
            key={props.question.questionId}
            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
        >
            <TableCell component="th" scope="row">
                <Typography variant="body">{props.question.questionDetails}</Typography>
            </TableCell>
            <TableCell component="th" scope="row">
                {props.question.tags.map((tag) => (


                    <Chip className={"tableTag"} label={tag.tagName} variant="outlined" color="primary" size="small" />
                ))}

            </TableCell>
            <TableCell align="right" component="th" scope="row">
                <TextField
                    type="number"
                    name="Grade"
                    label="Grade"
                    InputProps={{ inputProps: { min: 0, max: 10 } }}
                    variant="filled"
                    value={grade}
                    onChange={handleChange}
                />
            </TableCell>
            <TableCell component="th" scope="row">
                <Button className={"tableButton"}  onClick={()=>handleAdd(props.question)} >Add</Button>
            </TableCell>
        </TableRow>

    );
}
