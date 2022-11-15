import React from 'react'
import {Stack} from "@mui/material";
import Box from "@mui/material/Box";

const Home = () => {
    return (
      <>
          <Stack
              direction="row"
              justifyContent="space-between"
              alignItems="center"
              spacing={2}
          >
              <Box>Item 1</Box>
              <Box>Item 2</Box>
              <Box>Item 3</Box>
          </Stack>
      </>
    )
}

export default Home
