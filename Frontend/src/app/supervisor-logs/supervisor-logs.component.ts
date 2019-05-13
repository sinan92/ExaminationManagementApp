import {Component, Input, OnInit} from '@angular/core';
import {Log} from '../models/log.model';
import {LogService} from '../services/logs.service';

@Component({
  selector: 'app-supervisor-logs',
  templateUrl: './supervisor-logs.component.html',
  styleUrls: ['./supervisor-logs.component.css']
})
export class SupervisorLogsComponent implements OnInit {
  @Input() logs: Log[] = [];

  constructor(private serv: LogService) { }

  ngOnInit() {
      this.serv.getLogs().subscribe(data => this.logs = data);
  }

}
