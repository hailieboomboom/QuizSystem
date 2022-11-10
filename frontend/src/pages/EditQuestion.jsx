import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import Button from '@mui/material/Button';
import SendIcon from '@mui/icons-material/Send';
import { useState } from 'react';
import { Container } from '@mui/material';
import { Link } from "react-router-dom";

const EditQuestion = () => {
    const [category, setCategory] = useState('');
    const [difficulty, setDifficulty] = useState('');
    const [type, setType] = useState('');


    return (
        <React.Fragment>
            <Container>
                <Typography variant="h6" gutterBottom>
                    Create Quiz
                </Typography>
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <FormControl variant="standard"  fullWidth>
                            <InputLabel id="demo-simple-select-standard-label">MCQ Question</InputLabel>
                            <TextField
                                required
                                id="question"
                                name="questionString"
                                label="Question"
                                fullWidth
                                autoComplete="choice"
                                variant="standard"
                            />
                        </FormControl>
                    </Grid>
                    <Grid item xs={12}>
                        <FormControl variant="standard"  fullWidth>
                            <InputLabel id="demo-simple-select-standard-label">Possible Answer</InputLabel>
                            <TextField
                                required
                                id="answer1"
                                name="answer1String"
                                label="Answer1"
                                fullWidth
                                autoComplete="choice"
                                variant="standard"
                            />

                        </FormControl>
                    </Grid>
                    <Grid item xs={12}>
                        <FormControl variant="standard"  fullWidth>
                            <InputLabel id="demo-simple-select-standard-label">Possible Answer</InputLabel>
                            <TextField
                                required
                                id="answer2"
                                name="answer2String"
                                label="Answer2"
                                fullWidth
                                autoComplete="choice"
                                variant="standard"
                            />
                        </FormControl>
                    </Grid>
                </Grid>
                <Grid item xs={12}>
                    <FormControl variant="standard"  fullWidth>
                        <InputLabel id="demo-simple-select-standard-label">Possible Answer</InputLabel>
                        <TextField
                            required
                            id="answer3"
                            name="answer3String"
                            label="Answer3"
                            fullWidth
                            autoComplete="choice"
                            variant="standard"
                        />
                    </FormControl>
                </Grid>

                    <Grid item xs={1}>

                        <Button  variant="outlined" as={Link} to="/EditQuestions">
                            Create
                        </Button>
                    </Grid>
            </Container>

        </React.Fragment>
    )
}

export default CreateQuiz
