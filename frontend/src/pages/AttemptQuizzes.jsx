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
import '../styles/QuizzesTableStyle.css';
import TextField from "@mui/material/TextField";

const AttemptQuizzes = () => {

    const [quizzes, setQuizzes] = React.useState([]);
    const [quiz, setQuiz] = useRecoilState(attemptQuizState);

    const filterOptions = createFilterOptions({
        matchFrom: 'start',
        stringify: (option) => option.title,
    });

    const quizCategories = [
        {title: "Interview"},
        {title: "Skill"},
        {title: "All"}
    ];

    React.useEffect(() => {
        axios.get("https://the-trivia-api.com/api/questions?limit=10").then((response) => {
            setQuizzes([...quizzes, response.data]);
        });
        axios.get("https://the-trivia-api.com/api/questions?limit=10").then((response) => {
            setQuizzes([...quizzes, response.data]);
        });
        }, []);
    console.log(quizzes);
    console.log(quiz);


    if (!quizzes) return null;
    return (
        <Grid
            container
            direction="column"
            justifyContent="flex-start"
            alignItems="center"
            spacing={3}
        >
            <Grid item>
                <Typography variant="h2" gutterBottom>
                    Available Quizzes
                </Typography>
            </Grid>

            <Grid
                item
                direction="row"
                justifyContent="space-between"
                alignItems="flex-start"
            >
                <Grid item />
                <Grid item>
                    <Autocomplete
                        disableClearable
                        id="filter-demo"
                        options={quizCategories}
                        getOptionLabel={(option) => option.title}
                        filterOptions={filterOptions}
                        sx={{ width: 300 }}
                        renderInput={(params) => <TextField {...params} label="Filter" />}
                    />
                </Grid>


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
                                        <Button variant="contained" onClick={() => setQuiz(row)} as={Link} to="/quiz" >
                                            Take Quiz
                                        </Button>
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
        </Grid>

    )
};

export default AttemptQuizzes;
