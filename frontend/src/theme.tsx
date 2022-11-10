import {createTheme} from "@material-ui/core";

export const theme = createTheme({
    palette: {
        primary: {
            main: '#125430'
        },
        secondary: {
            main: '#178841'
        },
        success: {
            main: '#125430'
        }
    },
    overrides:{
        MuiTypography:{
            body2: {
                fontSize: 16
            }
        }
    }
});


export default theme;
