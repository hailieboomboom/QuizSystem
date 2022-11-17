import React from 'react'
import { Link } from 'react-router-dom';
import TableContainer from "@mui/material/TableContainer";
import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import axios from "axios";
import Button from "@mui/material/Button";
import { useRecoilState } from 'recoil';
import { attemptQuizState } from '../recoil/Atoms'
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Autocomplete, { createFilterOptions } from '@mui/material/Autocomplete';
import '../styles/AvailableQuizzes.css';
import TextField from "@mui/material/TextField";
import CircularProgress from "@mui/material/CircularProgress";
import {getUserId} from "../utils/cookies";

const AttemptedQuizzes = () => {

    const [quizzes, setQuizzes] = React.useState([]);
    const [loading, setLoading] = React.useState(true);
    const [quiz, setQuiz] = useRecoilState(attemptQuizState);
    const [attempts, setAttempts] = React.useState([]);



    React.useEffect(() => {
        axios.get("http://localhost:8088/QuizSystem/api/quizAttempts/quizTaker/"+getUserId()+"").then((response) => {
            setAttempts(response.data);
            setLoading(false);
        });
    }, []);
    if (!getUserId()) return(
        <Grid
            container
            justifyContent="center"
            alignItems="center"
        >
            <Grid item>
                <Typography>Please Log in first.</Typography>
            </Grid>
        </Grid>

    );
    if (loading) return(
        <Grid
            container
            justifyContent="center"
            alignItems="center"
        >
            <Grid item>
                <Typography>Please Wait...</Typography>
                <CircularProgress />
            </Grid>
        </Grid>

    );

    return (
        <div className={"availableQuizzesContainer"}>

            <Typography className={"availableQuizzesTitle"} variant="h2" gutterBottom>
                Attempted Quizzes
            </Typography>
        <Grid
            container
            className={"ManageQuizzesBox"}
            direction="column"
            justifyContent="flex-start"
            alignItems="center"
            spacing={3}
        >
            <Grid  item>
                <TableContainer component={Paper} >
                    <Table className={"availableQuizzesTable"} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell >Quiz</TableCell>
                                <TableCell align="right">Attempt Number</TableCell>
                                <TableCell align="right">Grade Awarded</TableCell>
                                <TableCell align="right">Max Grade</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {attempts.map((attempt) => (
                                <TableRow
                                    key={attempt.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell component="th" scope="row">
                                        <Typography variant="body">{attempt.quizName}</Typography>
                                    </TableCell>
                                    <TableCell align="right">
                                        <Typography variant="body">{attempt.attemptNo}</Typography>
                                    </TableCell>
                                    <TableCell align="right">
                                        <Typography variant="body">{attempt.totalAwarded}</Typography>
                                    </TableCell>
                                    <TableCell align="right">
                                        <Typography variant="body">{attempt.maxGrade}</Typography>
                                    </TableCell>
                                    {/*<TableCell align="right">{row.carbs}</TableCell>*/}
                                    {/*<TableCell align="right">{row.protein}</TableCell>*/}
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Grid>
        </Grid>
        </div>

    )
};

export default AttemptedQuizzes;
