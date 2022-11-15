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
import { createQuizAllQuestions, createQuizSelectedQuestions } from '../recoil/Atoms'
import TextField from "@mui/material/TextField";

export default function QuizSelectedQuestionsTableRow(props) {
    const [quizAllQuestions, setquizAllQuestions] = useRecoilState(createQuizAllQuestions);
    const [quizSelectedQuestions, setquizSelectQuestions] = useRecoilState(createQuizSelectedQuestions);
    const [grade, setGrade] = React.useState(props.question.grade);



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
                            <TableCell align="right" component="th" scope="row">
                                <Typography variant="body">{props.question.grade}</Typography>
                            </TableCell>
                            <TableCell component="th" scope="row"s>
                                <Button variant="outlined" onClick={()=>handleRemove(props.question)} >Remove</Button>
                            </TableCell>
                        </TableRow>

    );
}
