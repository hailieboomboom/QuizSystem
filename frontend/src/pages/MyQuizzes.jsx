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
import {isLoggedIn,setCookie, deleteCookie, getUserId} from "../utils/cookies"
import {attemptQuizState, createQuizAllQuestions, createQuizSelectedQuestions, editResponseState} from '../recoil/Atoms'
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import '../styles/QuizzesTableStyle.css';
import CircularProgress from "@mui/material/CircularProgress";

const MyQuizzes = () => {


    const [quizzes, setQuizzes] = React.useState('');
    const [loading, setLoading] = React.useState(true);
    const [quizAllQuestions, setquizAllQuestions] = useRecoilState(createQuizAllQuestions);
    const [quizSelectedQuestions, setquizSelectQuestions] = useRecoilState(createQuizSelectedQuestions);
    const [eMessage, setEMessage] = useRecoilState(editResponseState);
    const [dummy, setDummy] = React.useState('');
    const [quiz, setQuiz] = useRecoilState(attemptQuizState);

    React.useEffect(() => {
        fetchQuizzes();


    }, []);

    const fetchQuizzes = () => {
        axios.get("http://localhost:8088/QuizSystem/api/quizzes/users/" + getUserId() + "").then((response) => {
            setQuizzes(response.data);
            console.log(response.data);
            setLoading(false);
        }).catch(function (error) {
            console.log(error);
        });
    }
    const deleteQuiz = (id) => {
        axios.delete("http://localhost:8088/QuizSystem/api/quizzes/" + id + "").then(function (response) {
                console.log(response);
                setDummy(id);
            })
            .catch(function (error) {
                console.log(error);
            });


    }
    const handleDelete = (id) =>{
        deleteQuiz(id);
        setQuizzes((quizzes) =>
            quizzes.filter((quiz) => quiz.quizId !== id)
        );
    }


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
    if (quizzes.length<1) return(
        <Grid
            container
            justifyContent="center"
            alignItems="center"
        >
            <Typography variant="h4" gutterBottom>
                You have not created any quiz. Click below to create.
            </Typography>
            <Grid item sx={{ width:650 }}>
                <Button fullWidth color="success" variant="outlined" size="large" as={Link} to="/createQuiz">Create Quiz</Button>
            </Grid>
        </Grid>

    );

    return (
        <Grid
            container
            direction="column"
            justifyContent="flex-start"
            alignItems="center"

        >
            <Grid item>
                <Typography variant="h4" gutterBottom>
                    Available Quizzes
                </Typography>
            </Grid>
            <Grid item>
                <TableContainer className={"table"} component={Paper} sx={{ width:700 }}>
                    <Table sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell>Quizzes</TableCell>
                                <TableCell align="right">Action</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {
                                quizzes.map((row) => (
                                <TableRow
                                    key={row.name}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell component="th" scope="row">
                                        {row.name}
                                    </TableCell>
                                    <TableCell align="right">
                                        <Grid
                                            item
                                            container
                                            direction="row"
                                            justifyContent="flex-end"
                                            alignItems="flex-start"
                                            spacing={1}
                                        >
                                            <Grid item>
                                                <Button variant="contained" state={{ editQuiz: row }} as={Link} to="/editQuiz" >
                                                    Edit
                                                </Button>
                                            </Grid>
                                            <Grid item>
                                                <Button variant="contained" onClick={() => handleDelete(row.quizId)}>
                                                    Remove
                                                </Button>
                                            </Grid>
                                        </Grid>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Grid>

            <Grid item sx={{ width:650 }}>
                <Button fullWidth color="success" variant="outlined" size="large" as={Link} to="/createQuiz">Create Quiz</Button>
            </Grid>

        </Grid>

    )
};

export default MyQuizzes;
