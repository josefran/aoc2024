param (
    [Parameter(Mandatory=$true)]
    [int]$DayNumber,
    [Parameter(Mandatory=$true)]
    [string]$SessionToken
)

$DayDir = "../src/test/resources/day$DayNumber"
$InputFile = "$DayDir/input"

# Create the directory if it doesn't exist
if (-not (Test-Path -Path $DayDir)) {
    New-Item -ItemType Directory -Path $DayDir
}

# Download the input file
$Url = "https://adventofcode.com/2024/day/$DayNumber/input"
$Headers = @{
    "Cookie" = "session=$SessionToken"
}

$response = Invoke-WebRequest -Uri $Url -Headers $Headers -OutFile "$InputFile.tmp" -ErrorAction Stop

# Check if the download was successful
if ($response.StatusCode -eq 200) {
    Move-Item -Path "$InputFile.tmp" -Destination $InputFile
    Write-Output "Input for day $DayNumber downloaded successfully to $InputFile."
} else {
    Remove-Item -Path "$InputFile.tmp"
    Write-Output "Failed to download input for day $DayNumber. HTTP status: $($response.StatusCode)"
    exit 1
}