import * as jose from "jose";

export const verifyJwt = async (token) => {
    const jwtSecretKey = await import.meta.env.VITE_JWT_SECRET_KEY;
    try {
        const key = await jose.importJWK({
            alg: 'SHA-384',
            kty: 'oct',
            k: jwtSecretKey,
        });
        const {payload} = await jose.jwtVerify(token, key);
        return payload;
    } catch (err) {
        console.log(err);
    }
}