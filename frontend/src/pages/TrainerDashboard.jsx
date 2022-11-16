import * as React from 'react';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import {Container} from "@mui/material";
import Typography from "@mui/material/Typography";
import { apis } from '../utils/apis';
import {useState, useEffect} from "react";
import Button from '@mui/material/Button';
import StudentTable from "../components/StudentTable";

export default function TrainerDashboard(){

    const [rowData, setRowdata] = useState([]);
    const [students, setStudents] = useState([]);
    const [username, setUsername] = useState();
    useEffect(() => {
        return () => {
            apis.getUnauthorizedTrainers()
                .then(res => {
                    setRowdata(res.data)
                    console.log(res.data)
                })
        };
    }, [username]);

    const handleAuthorise = (username) => {
        setUsername(username);
        apis.authorizeTrainer(username).then(
            res => {
                console.log(res.data)
            }
        ).catch(
            err => console.log(err)
        )
    }

    return (
<Container>
    <div className={"trainerDashboardContainer"}>
        <div className={"trainerDashboardBox"}>
            <h1>Trainer Dashboard</h1>
            <br/>
            <TableContainer component={Paper}>
                <h1>Authorize Trainers</h1>
                <Table aria-label="simple table" stickyHeader>
                    <TableHead>
                        <TableRow>
                            <TableCell >Username</TableCell>
                            <TableCell >First Name</TableCell>
                            <TableCell >Last Name</TableCell>
                            <TableCell >Email</TableCell>
                            <TableCell >Role</TableCell>
                            <TableCell >Authorize</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {rowData.map((row) => (
                            <TableRow key={row.username}>
                                <TableCell component="th" scope="row" key={row.id}>
                                    {row.username}
                                </TableCell>
                                <TableCell  >{row.firstName}</TableCell>
                                <TableCell>{row.lastName}</TableCell>
                                <TableCell >{row.email}</TableCell>
                                <TableCell >{row.role}</TableCell>
                                <TableCell >
                                    <Button variant="outlined" onClick={()=>{handleAuthorise(row.username)}}>Authorize</Button>
                                </TableCell>

                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <br/>
            <h1>Student List</h1>
            <StudentTable></StudentTable>
        </div>
    </div>
</Container>


    
    );
}