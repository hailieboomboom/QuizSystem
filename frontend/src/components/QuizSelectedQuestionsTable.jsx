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
import QuizSelectedQuestionsTableRow from "./QuizSelectedQuestionsTableRow";

export default function QuizSelectedQuestionsTable() {
    const [quizAllQuestions, setquizAllQuestions] = useRecoilState(createQuizAllQuestions);
    const [quizSelectedQuestions, setquizSelectQuestions] = useRecoilState(createQuizSelectedQuestions);

    function hanldleRemove (current) {
        setquizSelectQuestions((questions) =>
            questions.filter((question) => question.mcqId !== current.mcqId)
        );
        setquizAllQuestions([...quizAllQuestions,current]);
    }
    return (
        <TableContainer className={"table"} component={Paper} sx={{ width:700 }}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Selected Questions</TableCell>
                        <TableCell align="right">Grade</TableCell>
                        <TableCell align="right">Action</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {quizSelectedQuestions.map((question) => (
                        <QuizSelectedQuestionsTableRow question={question}/>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
