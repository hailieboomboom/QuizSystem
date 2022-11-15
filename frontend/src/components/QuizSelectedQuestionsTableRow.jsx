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

    const handleChange = (event) => {
        setGrade(event.target.value);
        if(quizSelectedQuestions){
            setquizSelectQuestions((answers) =>
                answers.filter((answer) => answer.mcqId !== props.question.mcqId)
            );}
        setquizSelectQuestions(oldArray => [...oldArray,{
            "mcqId": props.question.mcqId,
            "grade": parseInt(event.target.value)
        }] );

    };

    function hanldleRemove (current) {
        setquizSelectQuestions((questions) =>
            questions.filter((question) => question.mcqId !== current.mcqId)
        );
        setquizAllQuestions([...quizAllQuestions,current]);
    }
    return (
                        <TableRow
                            key={props.question.mcqId}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row">
                                <Typography variant="body">{props.question.questionDetails}</Typography>
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
                            <TableCell component="th" scope="row"s>
                                <Button variant="outlined" onClick={()=>hanldleRemove(props.question)} >Remove</Button>
                            </TableCell>
                        </TableRow>

    );
}
