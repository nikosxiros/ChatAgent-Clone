import { useEffect, useState } from "react";
import styles from "../../styles/signup/SignupForm.module.css";
import axios from "axios";

export default function SignupForm() {
    useEffect(() => {
        document.body.classList.add(styles.bodyStyle);
        return () => {
            document.body.classList.remove(styles.bodyStyle);
        };
    }, []);

    const [form, setForm] = useState({
        name: "",
        email: "",
        password: "",
        confirmPassword: "",
    });

    const [message, setMessage] = useState("");
    const [passwordError, setPasswordError] = useState("");
    const [confirmPasswordError, setConfirmPasswordError] = useState("");
    const [showHint, setShowHint] = useState(false);
    const [strengthClass, setStrengthClass] = useState("");

    function handleChange(e) {
        const { name, value } = e.target;
        setForm((prev) => ({ ...prev, [name]: value }));
    }

    function validatePassword(password) {
        const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$/;
        return regex.test(password);
    }

    function getPasswordStrength(password) {
        if (password.length < 8) return "weak";
        if (password.match(/(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])/)) {
            return "strong";
        }
        return "medium";
    }

    async function handleSubmit(e) {
        e.preventDefault();
        const {name,email, password, confirmPassword } = form;

        if (!validatePassword(password)) {
            setPasswordError("Password does not meet the requirements.");
            return;
        } else {
            setPasswordError("");
        }

        if (password !== confirmPassword) {
            setConfirmPasswordError("Passwords do not match!");
            return;
        } else {
            setConfirmPasswordError("");
        }


        try{
            let respone = await axios.post("http://localhost:8080/users/Signup",{name, email, password})
                .then((respone)=>{
                setMessage("Signup Successful");
                console.log(respone.data);
                    window.location.href="/login"
            })
                .catch((error)=>{
                    setMessage("Signup Failed!");
                    console.error(error)
                })

        }catch (e){}



    }

    function handlePasswordInput(e) {
        const password = e.target.value;
        const strength = getPasswordStrength(password);
        setStrengthClass(strength);
    }





    return (
        <div className={styles.container}>
            <h2 className={styles.h2}>Signup Form</h2>
            <form onSubmit={handleSubmit}>
                <div className={styles.formGroup}>
                    <label className={styles.label} htmlFor="username">Username</label>
                    <input className={styles.input} type="text" name="name" id="name" required onChange={handleChange} />
                </div>

                <div className={styles.formGroup}>
                    <label className={styles.label} htmlFor="email">Email</label>
                    <input className={styles.input} type="email" name="email" id="email" required onChange={handleChange} />
                </div>

                <div className={styles.formGroup}>
                    <label className={styles.label} htmlFor="password">Password</label>
                    <input
                        className={styles.input}
                        type="password"
                        name="password"
                        id="password"
                        required
                        onChange={(e) => {
                            handleChange(e);
                            handlePasswordInput(e);
                        }}
                        onFocus={() => setShowHint(true)}
                        onBlur={() => setShowHint(false)}
                    />
                    {showHint && (
                        <div className={styles.passwordHint}>
                            Password must be at least 8 characters, with uppercase, lowercase, number, and special character.
                        </div>
                    )}
                    <span className={styles.error}>{passwordError}</span>
                    <div className={styles.passwordStrength}>
                        <div className={styles[strengthClass]}></div>
                    </div>
                </div>

                <div className={styles.formGroup}>
                    <label className={styles.label} htmlFor="confirmPassword">Confirm Password</label>
                    <input
                        className={styles.input}
                        type="password"
                        name="confirmPassword"
                        id="confirmPassword"
                        required
                        onChange={handleChange}
                    />
                    <span className={styles.error}>{confirmPasswordError}</span>
                </div>

                <button className={styles.button} type="submit">Sign Up</button>
            </form>

            {message && <p className={styles.message}>{message}</p>}
        </div>
    );
}
