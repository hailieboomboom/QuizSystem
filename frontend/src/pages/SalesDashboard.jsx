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
import Grid from "@mui/material/Grid";
import StudentTable from "../components/StudentTable";

export default function SalesDashboard(){
    const [sales, setSales] = useState([]);
    const [students, setStudents] = useState([]);
    const [username, setUsername] = useState();
    useEffect(() => {
        return () => {
            apis.getUnauthorizedSales()
                .then(res => {
                    setSales(res.data)
                    console.log(res.data)
                })
            apis.getAllStudents()
                .then(resp => {
                    setStudents(resp.data)
                    console.log(resp.data)
                })
        };
    }, [username]);

    const handleAuthorise = (username) => {
        setUsername(username);
        apis.authorizeSales(username).then(
            res => {
                console.log(res.data)
            }
        ).catch(
            err => console.log(err)
        )
    }

    return (
        <>

            <Container>
                <h1>Sales Dashboard</h1>
                <br/>
                        <TableContainer component={Paper}>
                            <h1>Authorize Sales</h1>
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
                                    {sales.map((row) => (
                                        <TableRow key={row.username}>
                                            <TableCell component="th" scope="row" key={row.username}>
                                                {row.username}
                                            </TableCell>
                                            <TableCell >{row.firstName}</TableCell>
                                            <TableCell >{row.lastName}</TableCell>
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
            </Container>
        </>

    );
}