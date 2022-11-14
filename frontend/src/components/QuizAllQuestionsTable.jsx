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

export default function QuizAllQuestionsTable() {
    const [quizAllQuestions, setquizAllQuestions] = useRecoilState(createQuizAllQuestions);
    const [quizSelectedQuestions, setquizSelectQuestions] = useRecoilState(createQuizSelectedQuestions);

    function hanldleAdd (current) {
        setquizAllQuestions((questions) =>
            questions.filter((question) => question.questionId !== current.questionId)
        );
        setquizSelectQuestions([...quizSelectedQuestions,current]);
    }
    console.log(quizAllQuestions);
    console.log(quizSelectedQuestions);


    return (
        <TableContainer className={"table"} component={Paper} sx={{ width:700 }}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Select from Questions</TableCell>
                        <TableCell align="right">Action</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {quizAllQuestions.map((question) => (
                        <TableRow
                            key={question.questionId}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row">
                                <Typography variant="body">{question.questionDetail}</Typography>
                            </TableCell>
                            <TableCell component="th" scope="row">
                                <Button variant="outlined" onClick={()=>hanldleAdd(question)} >Add</Button>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
