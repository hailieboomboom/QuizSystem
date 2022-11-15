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
import '../styles/QuizzesTableStyle.css';
import CircularProgress from "@mui/material/CircularProgress";

const MyQuizzes = () => {

    const [quizzes, setQuizzes] = React.useState([]);
    const [loading, setLoading] = React.useState(true);
    const [quiz, setQuiz] = useRecoilState(attemptQuizState);

    React.useEffect(() => {
        axios.get("https://the-trivia-api.com/api/questions?limit=10").then((response) => {
            setQuizzes([...quizzes, response.data]);
            setLoading(false);
        });
    }, []);
    console.log(quizzes);
    console.log(quiz);


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
        <Grid
            container
            direction="column"
            justifyContent="flex-start"
            alignItems="center"

        >
            <Grid item>
                <Typography variant="h4" gutterBottom>
                    Example 1 : You have not created any quiz. Click below to create.
                </Typography>
            </Grid>
            <Grid item>
                <Typography variant="h4" gutterBottom>
                    Example 2:Available Quizzes
                </Typography>
            </Grid>
            <Grid item>
                <TableContainer className={"table"} component={Paper} sx={{ width:700 }}>
                    <Table sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell>Quizzes</TableCell>
                                <TableCell align="right">Action</TableCell>
                                {/*<TableCell align="right">Fat&nbsp;(g)</TableCell>*/}
                                {/*<TableCell align="right">Carbs&nbsp;(g)</TableCell>*/}
                                {/*<TableCell align="right">Protein&nbsp;(g)</TableCell>*/}
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {quizzes.map((row) => (
                                <TableRow
                                    key={row.name}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell component="th" scope="row">
                                        This is A Demo Quiz
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
                                                <Button variant="contained" onClick={() => setQuiz(row)} as={Link} to="/quiz" >
                                                    Edit
                                                </Button>
                                            </Grid>
                                            <Grid item>
                                                <Button variant="contained" onClick={() => setQuiz(row)} as={Link} to="/quiz" >
                                                    Remove
                                                </Button>
                                            </Grid>
                                        </Grid>
                                    </TableCell>
                                    {/*<TableCell align="right">{row.fat}</TableCell>*/}
                                    {/*<TableCell align="right">{row.carbs}</TableCell>*/}
                                    {/*<TableCell align="right">{row.protein}</TableCell>*/}
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Grid>

            <Grid item sx={{ width:650 }}>
                <Button fullWidth color="success" variant="outlined" size="large">Create Quiz</Button>
            </Grid>

        </Grid>

    )
};

export default MyQuizzes;