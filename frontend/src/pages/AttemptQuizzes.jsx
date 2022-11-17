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

const AttemptQuizzes = () => {

    const [quizzes, setQuizzes] = React.useState([]);
    const [loading, setLoading] = React.useState(true);
    const [quiz, setQuiz] = useRecoilState(attemptQuizState);

    const filterOptions = createFilterOptions({
        matchFrom: 'start',
        stringify: (option) => option.title,
    });

    const quizCategories = [
        {
            title: "Interview Prep",
            type: "INTERVIEW_QUIZ"
        },
        {
            title: "Course Content",
            type: "COURSE_QUIZ"
        },
        {
            title: "All",
            type: ""
        }
    ];

    React.useEffect(() => {
        axios.get("http://localhost:8088/QuizSystem/api/quizzes").then((response) => {
            setQuizzes(response.data);
            setLoading(false);
        });
        }, []);
    console.log(quizzes);

    const [inputCategory, setInputCategory] = React.useState("");
    let categoryHandler = (e,v) => {
        //convert input text to lower case
        console.log(e);
        console.log(v);
        var lowerCase = v.type.toLowerCase();
        setInputCategory(lowerCase);
    };
    const filteredData = quizzes.filter((od) => {
        console.log(od);
        if (inputCategory === '') {
            return od;
        }
        //return the item which contains the user input
        else {
            console.log(od.quizCategory);
            return od.quizCategory.toLowerCase().includes(inputCategory)
        }
    })

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
                Available Quizzes
            </Typography>
        <Grid
            container
            className={"availableQuizzesBox"}
            direction="column"
            justifyContent="flex-start"
            alignItems="center"
            spacing={3}
        >

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
                        onChange={categoryHandler}
                        renderInput={(params) => <TextField {...params} label="Filter" />}
                    />
                </Grid>


            </Grid>

            <Grid item>
                <TableContainer component={Paper} sx={{ width:700 }}>
                    <Table className={"availableQuizzesTable"} sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell>Quizzes</TableCell>
                                <TableCell align="right">Category</TableCell>
                                <TableCell align="right">Action</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {filteredData.map((row) => (
                                <TableRow
                                    key={row.name}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell component="th" scope="row">
                                        {row.name}
                                    </TableCell>
                                    <TableCell align="right">{row.quizCategory}</TableCell>
                                    <TableCell align="right">
                                        <Button variant="contained" onClick={() => setQuiz(row)} as={Link} to="/quiz" >
                                            Take Quiz
                                        </Button>
                                    </TableCell>
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

export default AttemptQuizzes;
