
import styles from '../../styles/login/login.module.css';
import Head from 'next/head';
import axios from "axios";
import {useEffect, useState} from "react";



export default function login(){
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        document.body.classList.add(styles.bodyStyle);
        return () => {
            document.body.classList.remove(styles.bodyStyle);
        };
    }, []);

    useEffect(() => {

        let token = localStorage.getItem("token");

        if (token != null) {
            window.location.href = "/chat";
        }
    }, []);




    function handleEmailChange(event) {
        console.log("Email changed to: ", event.target.value);
        setEmail(event.target.value);
    }

    function handlePasswordChange(event) {
        setPassword(event.target.value);
    }

    async  function handleLogin(){
        event.preventDefault();
        setLoading(true);







        let credentials = btoa(email + ":" + password);




        try {
            let response= await axios.post("http://localhost:8080/users/login", {},{
                headers:{"Authorization": `Basic ${credentials}`,
                    "Content-Type": "application/json"
                }
            } )
            console.log("Login Successful");

            console.log("RESPONSE",response.data);


            localStorage.setItem("token",response.data.token);
            setLoading(false);

            window.location.href="/chat"

        }catch (e){
            console.log("Login Failed");
            alert("Login Failed. Please check your credentials");
            setEmail("");
            setPassword("");
            setLoading(false);
        }




    }


    return(
        <>

            <head>
                <title>Login</title>
            </head>


            <div className={styles.bodyStyle} >
            <div className="main">

                <h1>Welcome to AI Agent Clone</h1>
                <h3>Enter your index credentials</h3>

                <form onSubmit={handleLogin}>

                    <label className={styles.label} htmlFor="first">Email:</label>
                    <input className={styles.input} type="text" id="first" name="first" placeholder="Enter your Email" required
                           value={email}
                           onChange={handleEmailChange}
                    />

                    <label className={styles.label} htmlFor="password">Password:</label>
                    <input className={styles.input} type="password" id="password" name="password" placeholder="Enter your Password" required
                           value={password}
                           onChange={handlePasswordChange}
                    />

                    <div className="wrap">
                        <button className={styles.button} type="submit"> Login</button>
                    </div>
                </form>

                <p>Not registered? <a href="signup">Create an account</a>
                </p>

                <p><a href="guestChat">Stay Signed Out</a></p>



            </div>
                </div>

        </>
    );
}