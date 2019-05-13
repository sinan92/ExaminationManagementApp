export class Examen {
  id: number;
  naam: string;
  skelet: string;

  constructor(
    id: number,
    naam: string,
    link: string,
    skelet: string
  ) {
    this.id = id;
    this.naam = naam;
    this.skelet = skelet;
  }
}
