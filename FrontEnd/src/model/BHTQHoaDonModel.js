export class BHTQHoaDonModel {
    idHTGH;
    idPTTT;
    moTa;
    tienKhachDua;
    idDiaChi;
    tienShip;

    constructor(idHTGH, idPTTT, moTa, tienKhachDua, idDiaChi,tienShip) {
        this.idHTGH = idHTGH;
        this.idPTTT = idPTTT;
        this.moTa = moTa;
        this.tienKhachDua = tienKhachDua;
        this.tienShip = tienShip;
        this.idDiaChi = idDiaChi;
    }
}