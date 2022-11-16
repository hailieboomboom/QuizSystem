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
import {createQuizAllQuestions, createQuizSelectedQuestions} from '../recoil/Atoms'
import TextField from "@mui/material/TextField";
import QuizSelectedQuestionsTableRow from "./QuizSelectedQuestionsTableRow";
import QuizAllQuestionsTableRow from "./QuizAllQuestionsTableRow";

export default function QuizAllQuestionsTable() {
    const [quizAllQuestions, setquizAllQuestions] = useRecoilState(createQuizAllQuestions);
    const [quizSelectedQuestions, setquizSelectQuestions] = useRecoilState(createQuizSelectedQuestions);


    function hanldleAdd (current) {
        setquizAllQuestions((questions) =>
            questions.filter((question) => question.questionId !== current.questionId)
        );
        setquizSelectQuestions([...quizSelectedQuestions,current]);
    }
    const [inputText, setInputText] = React.useState("");
    let inputHandler = (e) => { 
        //convert input text to lower case
        var lowerCase = e.target.value.toLowerCase();
        setInputText(lowerCase);
    };
    const filteredData = quizAllQuestions.filter((od) => {
        //if no input the return the original
        if (inputText === '') {
            return od;
        }
        //return the item which contains the user input
        else {
            return od.questionDetails.toLowerCase().includes(inputText)
        }
    })


    console.log(quizAllQuestions);
    console.log(quizSelectedQuestions);

    return (
        <TableContainer className={"table"} component={Paper} sx={{ width:700 }}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Select from Questions</TableCell>
                        <TableCell>
                            <TextField
                                id="outlined-basic"
                                onChange={inputHandler}
                                variant="filled"
                                fullWidth
                                size="small"
                                label="Search"
                            />
                        </TableCell>
                        <TableCell align="right">Action</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {filteredData.map((question) => (

                        <QuizAllQuestionsTableRow question={question}/>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
