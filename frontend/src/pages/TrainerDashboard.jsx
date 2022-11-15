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

export default function TrainerDashboard(){
    const [data, setData] = useState([]);
    const [rowData, setRowdata] = useState([]);
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
        <TableContainer component={Paper}>
            <Table aria-label="simple table" stickyHeader>
                <TableHead>
                    <TableRow>
                        <TableCell align="right">Username</TableCell>
                        <TableCell align="right">First Name</TableCell>
                        <TableCell align="right">Last Name</TableCell>
                        <TableCell align="right">Email</TableCell>
                        <TableCell align="right">Role</TableCell>
                        <TableCell align="right">Authorize</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rowData.map((row) => (
                        <TableRow key={row.username}>
                            <TableCell component="th" scope="row" key={row.id}>
                                {row.username}
                            </TableCell>
                            <TableCell align="right" key={row.id}>{row.firstName}</TableCell>
                            <TableCell align="right" key={row.id}>{row.lastName}</TableCell>
                            <TableCell align="right" key={row.id}>{row.email}</TableCell>
                            <TableCell align="right" key={row.id}>{row.role}</TableCell>
                            <TableCell align="right" key={row.id}>
                                <Button variant="outlined" onClick={()=>{handleAuthorise(row.username)}}>Authorize</Button>
                            </TableCell>
                        
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    
    );
}