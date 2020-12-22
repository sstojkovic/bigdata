class TimeRange extends Serializable {
  var start: Int = 0
  var end: Int = 0

  def this(start: Int, end: Int) = {
    this()
    this.start = start
    this.end = end
  }
}
